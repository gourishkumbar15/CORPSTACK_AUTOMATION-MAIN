package generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * RetryListener class to automatically apply RetryAnalyzer to all test methods
 * This class implements IAnnotationTransformer interface to modify test annotations at runtime
 */
public class RetryListener implements IAnnotationTransformer {
    
    /**
     * This method is called for every test method and allows us to modify its annotation
     * 
     * @param annotation The test annotation
     * @param testClass The test class
     * @param testConstructor The test constructor
     * @param testMethod The test method
     */
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Set retry analyzer for all test methods
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
