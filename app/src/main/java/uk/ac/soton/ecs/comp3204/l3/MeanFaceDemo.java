package uk.ac.soton.ecs.comp3204.l3;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JPanel;

import org.openimaj.content.slideshow.Slide;
import org.openimaj.content.slideshow.SlideshowApplication;
import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.image.DisplayUtilities.ImageComponent;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.resize.ResizeProcessor;

import uk.ac.soton.ecs.comp3204.l3.FaceDatasetDemo.FaceDatasetProvider;
import uk.ac.soton.ecs.comp3204.utils.Utils;
import uk.ac.soton.ecs.comp3204.utils.annotations.Demonstration;

/**
 * Visualise the mean face
 * 
 * @author Jonathon Hare (jsh2@ecs.soton.ac.uk)
 * 
 */
@Demonstration(title = "Mean face demo")
public class MeanFaceDemo implements Slide {

	@Override
	public Component getComponent(int width, int height) throws IOException {
		final VFSGroupDataset<FImage> dataset = FaceDatasetProvider.getDataset();

		final FImage img = dataset.getRandomInstance().fill(0f);
		for (final FImage i : dataset) {
			img.addInplace(i);
		}
		img.divideInplace(dataset.numInstances()).processInplace(new ResizeProcessor(6.0f));

		final JPanel outer = new JPanel();
		outer.setOpaque(false);
		outer.setPreferredSize(new Dimension(width, height));
		outer.setLayout(new GridBagLayout());

		final ImageComponent ic = new ImageComponent(true, false);
		ic.setAllowPanning(false);
		ic.setAllowZoom(false);
		ic.setShowPixelColours(false);
		ic.setShowXYPosition(false);
		ic.setImage(ImageUtilities.createBufferedImageForDisplay(img));
		outer.add(ic);

		return outer;
	}

	@Override
	public void close() {
		// do nothing
	}

	public static void main(String[] args) throws IOException {
		new SlideshowApplication(new MeanFaceDemo(), 1024, 768, Utils.BACKGROUND_IMAGE);
	}
}
