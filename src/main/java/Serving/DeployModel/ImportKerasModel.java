package Serving.DeployModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFileChooser;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.api.iter.NdIndexIterator;
import org.deeplearning4j.nn.modelimport.keras.*;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

/**
 * Loading, serving and consuming a Keras model to classify panoramas.
 *
 * @author Marco Giuseppe de Pinto
 */

public class ImportKerasModel {
	
	// Logger
	private static final Logger LOGGER = LogManager.getLogger( ImportKerasModel.class.getName());

	// This will create a popup window to allow you to chose an image file to test against the trained Neural Network
	public static String fileChose() {
	  JFileChooser fc = new JFileChooser();
	  int ret = fc.showOpenDialog(null);
	  if (ret == JFileChooser.APPROVE_OPTION) {
	    File file = fc.getSelectedFile();
	    String filename = file.getAbsolutePath();
	    return filename;
	  } else {
	    return null;
	  }
    }
	
	public static void main(String[] args) throws Exception {
		
		String simpleMlp = new ClassPathResource
				("resources/scenarioclassifier_91_F1_200x200_Size_3Ep.h5").getFile().getPath();
		LOGGER.info("SimpleMlp created");
		
		MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(simpleMlp);
		LOGGER.info("Model loaded");
		
		// Creating label list and printing it
		
		ArrayList<String> list = new ArrayList<String>() {{
		    add("city");
		    add("desert");
		    add("mountain");
		    add("nature");
		    add("sea");
		    add("universe");
		}};
		
		System.out.println(list);
		
		int height = 200;
	    int width = 200;
	    int channels = 3;
	    
	    String filechose = fileChose().toString();
	    
	    File file = new File(filechose);
	    
	    // Use NativeImageLoader to convert to numerical matrix
	    NativeImageLoader loader = new NativeImageLoader(height, width, channels);
	    
	    // Put the image into an INDArray
	    INDArray image = loader.asMatrix(file);
	    // System.out.println(image);
	    
	    // Predicting
	    INDArray output = model.output(image);
	    
	    LOGGER.info("The file chosen was " + filechose);
	    LOGGER.info("The neural net class prediction in percentage)");
	    LOGGER.info(output.toString());
	    
	    // Printing predictions
	    String result = output.toString();
	    System.out.println(result); 
		
	    LOGGER.info("Looping through the predictions to assign a label)");
	    
	    String word;
	    ArrayList<Double> array = new ArrayList<Double>();
	    
		NdIndexIterator iter = new NdIndexIterator(output.shape());
	    while (iter.hasNext()) {
	        long[] nextIndex = iter.next();
	        double nextVal = output.getDouble(nextIndex);
	        // System.out.println(nextVal);
	        array.add(nextVal);
	    }
	    
	 System.out.println(array);
	 
	 Integer maxValueIdx = array.indexOf(Collections.max(array));
	        
	 word = list.get((int) maxValueIdx);
	 System.out.println(word); 
	 System.exit(0);
	
	}

}