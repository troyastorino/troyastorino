# Hiding code with Emacs: HideShow Minor Mode

When editing a file with a lot of code, I often find myself navigating through
the same function definitions over and over again. This gets pretty annoying, so
I wanted to be able to hide the code for some functions (or classes), just like
you can with the little +/- toggle buttons to the left of code in many graphical
IDEs. Of course, Emacs had plenty of solutions lying around just waiting for me
to discover them. I quickly found everything I needed to do all the code folding
my heart desired.

The
[HideShow minor mode](http://www.gnu.org/software/emacs/manual/html_node/emacs/Hideshow.html)
was exactly what I was looking for. It's built into Emacs 20 and later, and is
enabled with `M-x hs-minor-mode`. It hides between delimiter characters,
changing depending on the language of the code you're editing, e.g. for C-type
languages it hides between brackets, but for Lisps it hides between parens. The
commands I find most useful are:
* `C-c @ C-c` toggles hide/show for the code block you're in
* `C-c @ C-M-h` hides all top-level blocks
* `C-c @ C-M-s` shows all top level blocks

Of course, I wanted to automatically enable this minor mode in the editing major
modes that I'm often in, so I added the following to my initialization file:

```elisp
(defvar code-editing-mode-hooks '(c-mode-common-hook
                                  clojure-mode-hook
                                  emacs-lisp-mode-hook
                                  java-mode-hook
                                  lisp-mode-hook
                                  perl-mode-hook
                                  python-mode-hook
                                  sh-mode-hook))

;; Add a hs-minor-mode hook to code editing major modes
(dolist (mode code-editing-mode-hooks)
  (add-hook mode 'hs-minor-mode))
```
