package de.chille.mme.mapper;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import de.chille.api.mds.core.*;
import de.chille.api.mme.mapper.UnicodeMapper;
import de.chille.mds.core.*;
import de.chille.mds.soap.MDSMapperBean;
import de.chille.mme.MMEGlobals;
import de.chille.mme.core.MetaMappingEngineException;

/**
 * @see UnicodeMapper
 * 
 * @author Thomas Chille
 */
public class UnicodeMapperImpl extends MDSMapperImpl implements UnicodeMapper {

	private Vector classes = new Vector();
	private Vector assos = new Vector();
	private Vector genis = new Vector();

	/**
	 * Constructor for UnicodeMapperImpl.
	 * @param id
	 */
	public UnicodeMapperImpl(String id) {
		super(id);
	}

	/**
	 * @see UnicodeMapper#generateParser()
	 */
	public synchronized void generateParser()
		throws MetaMappingEngineException {
		String path = MMEGlobals.MAPPER_PATH + "/mapper_" + this.getId();
		// package anpassen
		strFileReplace(
			path + "/parse.jj",
			"package de\\.chille\\.mme\\.mapper;",
			"package de.chille.mme.mapper.mapper_" + this.getId() + ";");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream outputPrintStream, oldOutStream = System.out;
		// argumente setzen
		String[] javaCCargs =
			{ "-OUTPUT_DIRECTORY=" + path, path + "/parse.jj" };
		String[] javaCargs =
			{
				path + "/JavaCharStream.java",
				path + "/MDSParser.java",
				path + "/MDSParserConstants.java",
				path + "/MDSParserTokenManager.java",
				path + "/ParseException.java",
				path + "/Token.java",
				path + "/TokenMgrError.java" };
		// meldungen von stdout nach outputPrintStream umleiten
		outputPrintStream = new PrintStream(outputStream);
		System.setOut(outputPrintStream);
		int errorcode = 0;
		String msg = "";
		try {
			// *.java files generieren
			if ((errorcode = COM.sun.labs.javacc.Main.mainProgram(javaCCargs))
				== 0) {
				// *.class files generieren
				errorcode =
					com.sun.tools.javac.Main.compile(
						javaCargs,
						new PrintWriter(System.out));
			}
			outputPrintStream.close();
			System.setOut(oldOutStream);
			// outputPrintStream nach inputStream pipen
			ByteArrayInputStream inputStream =
				new ByteArrayInputStream(outputStream.toByteArray());
			BufferedReader reader =
				new BufferedReader(new InputStreamReader(inputStream));
			// fehlermeldungen aus inputStream lesen
			String line;
			while ((line = reader.readLine()) != null) {
				msg += line + "\n";
			}
			reader.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (errorcode != 0) {
			throw new MetaMappingEngineException(msg);
		}
	}

	/**
	 * @see Mapper#map(MDSModel)
	 */
	public synchronized void map(MDSModel mdsModel)
		throws MetaMappingEngineException {

		classes = new Vector();
		assos = new Vector();
		genis = new Vector();

		ByteArrayInputStream inputStream;
		MDSFile file = null;
		try {
			// parser-klasse dynamisch laden
			MDSParserInterface parser =
				(MDSParserInterface) Class
					.forName(
						"de.chille.mme.mapper.mapper_"
							+ this.getId()
							+ ".MDSParser")
					.newInstance();
			// alle AdditionalFiles parsen			
			Iterator it = mdsModel.getAdditionalFiles().iterator();
			while (it.hasNext()) {
				file = (MDSFileImpl) it.next();
				inputStream =
					new ByteArrayInputStream(file.getContent().getBytes());
				parser.parse(this, inputStream);
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MetaMappingEngineException(
				e.getMessage(),
				file.getPath());
		}
		insertElements(mdsModel);
	}

	public void addMDSClassAction(String name) {
		classes.add(name);
	}

	public void addMDSGeneralizationAction(
		String subName,
		String superName,
		String name) {
		genis.add(new String[] { subName, superName, name });
	}

	public void addMDSAssoziationAction(
		String end1name,
		String end2name,
		String name) {
		assos.add(new String[] { end1name, end2name, name });
	}

	private void insertElements(MDSModel mdsModel) {
		try {
			mdsModel.setElements(new ArrayList());
			mdsModel.setCounter(0);
			MDSClass mdsClass;
			Iterator it = classes.iterator();
			while (it.hasNext()) {
				mdsClass = new MDSClassImpl();
				mdsClass.setLabel((String) it.next());
				mdsModel.insertElement(mdsClass);
			}
			MDSGeneralization mdsGeni;
			it = genis.iterator();
			while (it.hasNext()) {
				String[] strs = (String[]) it.next();
				if (!(classes.contains(strs[0])
					&& classes.contains(strs[1]))) {
					continue;
				}
				mdsGeni = new MDSGeneralizationImpl();
				mdsGeni.setLabel(strs[2]);
				mdsGeni.setSubClass(
					(MDSClassImpl) mdsModel.getElementByLabel(strs[0]));
				mdsGeni.setSuperClass(
					(MDSClassImpl) mdsModel.getElementByLabel(strs[1]));
				mdsModel.insertElement(mdsGeni);
			}
			MDSAssociation mdsAsso;
			MDSAssociationEnd end;
			ArrayList ends = new ArrayList();
			it = assos.iterator();
			while (it.hasNext()) {
				String[] strs = (String[]) it.next();
				if (!(classes.contains(strs[0])
					&& classes.contains(strs[1]))) {
					continue;
				}
				mdsAsso = new MDSAssociationImpl();
				mdsAsso.setLabel(strs[2]);
				end = new MDSAssociationEndImpl();
				end.setMdsClass(
					(MDSClassImpl) mdsModel.getElementByLabel(strs[0]));
				ends.add(end);
				end = new MDSAssociationEndImpl();
				end.setMdsClass(
					(MDSClassImpl) mdsModel.getElementByLabel(strs[1]));
				ends.add(end);
				mdsAsso.setAssociationEnds(ends);
				mdsModel.insertElement(mdsAsso);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MDSMapperBean exportBean() {
		MDSMapperBean bean = super.exportBean();
		bean.setType("unicode");
		return bean;
	}

	private void strFileReplace(
		String path,
		String regex,
		String replacement) {
		BufferedReader f;
		String line = null;
		String content = "";
		try {
			f = new BufferedReader(new FileReader(path));
			while ((line = f.readLine()) != null) {
				content += line + "\n";
			}
			f.close();
			FileWriter fw;
			fw = new FileWriter(path);
			fw.write(content.replaceFirst(regex, replacement));
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}