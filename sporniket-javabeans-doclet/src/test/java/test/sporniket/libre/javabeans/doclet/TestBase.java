/**
 * 
 */
package test.sporniket.libre.javabeans.doclet;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base class for when a test needs common utilities.
 * 
 * @author dsporn
 *
 */
public class TestBase
{
	private ObjectMapper mapper = new ObjectMapper();

	private static final String FORMAT__DATA_FILE_PATH = "%s_data/%s";

	/**
	 * Reads a JSON resource file.
	 * 
	 * @param path
	 *            the path of the resource in the classpath.
	 * @param type
	 *            the type to obtain
	 * @return the extracted object
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	protected <T> T loadJsonData(String path, Class<T> type) throws JsonParseException, JsonMappingException, IOException
	{
		return mapper.readValue(getClass().getClassLoader().getResourceAsStream(path), type);
	}

	/**
	 * Compute the path of the ressource, that is put in a folder named after the class name with the "_data" suffix.
	 * 
	 * @param resourceName
	 *            the resource name, e.g. "my-data.json".
	 * @return the path, e.g. <code>"com/mypackage/MyClass_data/my-data.json"</code>.
	 */
	protected String getPathToDataRessource(String resourceName)
	{
		return format(FORMAT__DATA_FILE_PATH, getClass().getName().replace('.', File.separatorChar), resourceName);
	}

	protected InputStream getDataRessource(String resourceName)
	{
		return getClass().getClassLoader().getResourceAsStream(getPathToDataRessource(resourceName));
	}

}
