(require '[clj-jgit.porcelain :as git])

(let [repo (git/load-repo ".")
      start-branch (git/git-branch-current repo)
      had-uncommitted (not-empty (:modified (git/git-status repo)))
      old-index (slurp "resources/public/index.html")
      new-index (clojure.string/replace old-index
                  "src=\"" "src=\"resources/public/")
      old-ignore (slurp ".gitignore")
      new-ignore (clojure.string/replace old-ignore
                   #"resources/public\S*" "")
      gh-pages-exists (some (fn [branch]
                              (= "refs/heads/gh-pages"
                                 (.getName branch)))
                            (git/git-branch-list repo))]
  (when had-uncommitted
    (println "Committing changes temporarily.")
    (git/git-add-and-commit repo
      "Temporary commit while publishing."))
  (when (and gh-pages-exists
             (not= "gh-pages" start-branch))
    (git/git-branch-delete repo ["gh-pages"] true))
  (git/git-checkout repo "gh-pages" true)
  (git/git-reset repo start-branch :hard)
  (spit "index.html" new-index)
  (git/git-add repo "index.html")
  (spit ".gitignore" new-ignore)
  (clojure.java.shell/sh "lein" "do" "clean," "cljsbuild" "once" "min")
  (git/git-add repo "resources/public")
  (clojure.java.shell/sh "cp" "resources/public/favicon.ico" "favicon.ico")
  (git/git-add repo "favicon.ico")
  (git/git-add-and-commit repo "Publish to Github Pages")
  (clojure.java.shell/sh "git" "push" "-f" "origin" "gh-pages")
  (git/git-checkout repo start-branch)
  (when had-uncommitted
    (git/git-reset repo "HEAD~1")))

