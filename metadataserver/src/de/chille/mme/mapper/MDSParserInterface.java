package de.chille.mme.mapper;

import java.io.InputStream;

public interface MDSParserInterface
{
	public void parse(UnicodeMapperImpl mapper, InputStream input) 
		throws Exception;
}