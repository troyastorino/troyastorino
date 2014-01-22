var brushes = {
  'bash': ['sh', 'bash', 'shell'],
  'clojure': ['clojure', 'clj'],
  'cpp': ['c++', 'cpp', 'c'],
  'css': ['css', 'less'],
  'java': ['java'],
  'js': ['javascript', 'js', 'jscript'],
  'lisp': ['lisp', 'elisp'],
  'plain': ['text', 'plain'],
  'python': ['python', 'py'],
  'sql': ['sql'],
  'xml': ['xml', 'xhtml', 'html']
}
  , args = [];

for (var file in brushes) {
  var array = brushes[file];
  array.push('/lib/sh/' + file + '.js');
  args.push(array.join(' '));
}

$(function() {
  SyntaxHighlighter.autoloader.apply(null, args);
  SyntaxHighlighter.all();
});
