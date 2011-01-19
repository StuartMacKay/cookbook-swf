import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.flagstone.cookbook.BasicButton;
import com.flagstone.cookbook.BasicButtonSound;
import com.flagstone.cookbook.BasicImage;
import com.flagstone.cookbook.BasicLayers;
import com.flagstone.cookbook.BasicLevels;
import com.flagstone.cookbook.BasicMovie;
import com.flagstone.cookbook.BasicMovieClip;
import com.flagstone.cookbook.BasicScript;
import com.flagstone.cookbook.BasicShapes;
import com.flagstone.cookbook.BasicSound;
import com.flagstone.cookbook.BasicSoundtrack;
import com.flagstone.cookbook.BasicText;
import com.flagstone.cookbook.BasicTextBlock;
import com.flagstone.cookbook.BasicTextField;
import com.flagstone.cookbook.ChangeColour;
import com.flagstone.cookbook.ChangeOrientation;
import com.flagstone.cookbook.ChangeShape;
import com.flagstone.cookbook.ChangeSize;
import com.flagstone.cookbook.ChangeVisibility;

public class CookbookIT
{
	private static File destDir;
	private File destFile;

	@BeforeClass
	public static void setup()
	{
		destDir = new File("target/GenerateFileTest");

		if (!destDir.exists()) {
			assertTrue(destDir.mkdirs());
		}

		File[] files = destDir.listFiles();

		for (File file : files) {
			assertTrue(file.delete());
		}
	}

	@Test
	public void verifyBasicButton()
	{
		destFile = new File(destDir, "BasicButton.swf");

		BasicButton.main(new String[] {"Click Me!", destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicButtonSound()
	{
		destFile = new File(destDir, "BasicButtonSound.swf");

		BasicButtonSound.main(new String[] {"src/test/resources/welcome.mp3", destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicImage()
	{
		destFile = new File(destDir, "BasicImage.swf");

		BasicImage.main(new String[] {"src/test/resources/flagstone.png", destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicLayers()
	{
		destFile = new File(destDir, "BasicLayers.swf");

		BasicLayers.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicLevels()
	{
		destFile = new File(destDir, "BasicLevels.swf");

		BasicLevels.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicMovie()
	{
		destFile = new File(destDir, "BasicMovie.swf");

		BasicMovie.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicMovieClip()
	{
		destFile = new File(destDir, "BasicMovieClip.swf");

		BasicMovieClip.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicScript()
	{
		destFile = new File(destDir, "BasicScript.swf");

		BasicScript.main(new String[] {"src/test/resources/DragMovieClip.as", destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicShapes()
	{
		destFile = new File(destDir, "BasicShapes.swf");

		BasicShapes.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicSound()
	{
		destFile = new File(destDir, "BasicSound.swf");

		BasicSound.main(new String[] {"src/test/resources/welcome.wav", destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicSoundtrack()
	{
		destFile = new File(destDir, "BasicSoundtrack.swf");

		BasicSoundtrack.main(new String[] {"src/test/resources/welcome.mp3", destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicText()
	{
		destFile = new File(destDir, "BasicText.swf");

		BasicText.main(new String[] {"Welcome", destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicTextBlock()
	{
		destFile = new File(destDir, "BasicTextBlock.swf");

		BasicTextBlock.main(new String[] {"Welcome", destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyBasicTextField()
	{
		destFile = new File(destDir, "BasicTextField.swf");

		BasicTextField.main(new String[] {"Welcome", destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyChangeColour()
	{
		destFile = new File(destDir, "ChangeColour.swf");

		ChangeColour.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyChangeOrientation()
	{
		destFile = new File(destDir, "ChangeOrientation.swf");

		ChangeOrientation.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyChangeShape()
	{
		destFile = new File(destDir, "ChangeShape.swf");

		ChangeShape.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyChangeSize()
	{
		destFile = new File(destDir, "ChangeSize.swf");

		ChangeSize.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}

	@Test
	public void verifyChangeVisibility()
	{
		destFile = new File(destDir, "ChangeVisibility.swf");

		ChangeVisibility.main(new String[] {destFile.getPath()});
		assertTrue(destFile.exists());
	}
}
