# Keras model deploying with Java

Starting from another project I built to classify images using Keras (https://github.com/marcogdepinto/ScenarioClassifier), I worked on deepening my Java knowledge creating a class to consume the model already trained in Python.

The library used is [DeepLearning4j](https://deeplearning4j.org/).

# Output

![city](https://github.com/marcogdepinto/Java-KerasDLModelServing/blob/master/city2.jpg)

Passing the above picture as input, the class will return the following information

```
[city, desert, mountain, nature, sea, universe]
[[    1.0000,         0,         0,         0,         0,         0]]
[1.0, 0.0, 0.0, 0.0, 0.0, 0.0]
city
```

**Output explanation**

- The first line is an array with the labels.
- The second line includes an array with the predicted percentage per class.
- The third line is given as output to match the labels and the array of predictions.
- The forth line is the prediction in human-readable language.
