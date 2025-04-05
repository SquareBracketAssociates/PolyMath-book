(TeX-add-style-hook
 "sbabook"
 (lambda ()
   (TeX-add-to-alist 'LaTeX-provided-class-options
                     '(("sbabook" "english" "twoside" "openany" "showtrims")))
   (TeX-add-to-alist 'LaTeX-provided-package-options
                     '(("datetime2" "useregional") ("gitinfo2" "local" "dirty=*") ("hyperref" "unicode" "breaklinks" "hidelinks")))
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "path")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "url")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "nolinkurl")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "hyperbaseurl")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "hyperimage")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "hyperref")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "href")
   (add-to-list 'LaTeX-verbatim-macros-with-delims-local "path")
   (TeX-run-style-hooks
    "latex2e"
    "DhbDefinitions"
    "Preface"
    "Introduction"
    "Function"
    "Interpolation"
    "Iteration"
    "Zeroes"
    "Integration"
    "Series"
    "LinearAlgebra"
    "Statistics"
    "Estimation"
    "Minimization"
    "Bibliography"
    "sbabook10"
    "datetime2"
    "gitinfo2"
    "xstring"
    "multirow"
    "graphicx"
    "lstsmalltalk"
    "url"
    "hyperref")
   (TeX-add-symbols
    '("hrefnote" ["argument"] 2)
    "gitCommitInfo"
    "gitdate"
    "sectionline"
    "THEDAY"
    "THEMONTH"
    "THEYEAR"
    "UrlFont"))
 :latex)

