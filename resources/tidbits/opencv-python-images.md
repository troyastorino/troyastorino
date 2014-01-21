I've been working on a project using (OpenCV)[http://opencv.org/], and decided
to use the Python bindings instead of C++ because numpy is such a pleasure to
work with. For the most part, the bindings have worked very well, but, for some
functions I would get unexplained errors that would point to entirely unhelpful
lines in the C++ code.

After struggling with it for a little while, and tracing back though the source,
I realized that the OpenCV python bindings didn't coerce the numpy ndarray into
having the proper type for the C++ functions. I'm not sure if there is a good
reason for this behavior, but it's definitely something to watch out for.
Anyway, if you're getting errors with the OpenCV python bindings and you're
_sure_ your code is right, check to see what type of the elements of the ndarray
should be, and pass it to the OpenCV function using the `astype(type)` method.
