## Starting with GitHub Actions
#### 2021-02-01
###### meta technical

So I had GitHub hosting my site, and I had code that generated my site from 
Markdown files, which was great and free. But GitHub is just a static code 
repository, so I had to write a post and run the code that generates the
website locally before pushing it up to GitHub. GitHub can't run the code 
that generates the website server-side every time I push an update... or can it?

Enter GitHub Actions! I remembered that this exists and that it can do some 
sort of server-side code execution because my company uses it to run tests. 
So I got the default Clojure GitHub Actions template and created a test that 
would run the code that generates the website. Normally it would just throw 
away any changes that were made as a result of running tests, but I 
discovered that there's a GitHub Actions Marketplace full of cool Actions 
functions, including one by steganzweifel 
(https://github.com/marketplace/actions/git-auto-commit) that automatically 
commits code changes that happen during an Action.    

Putting all those things together, I now have a workflow where I can write a 
post in Markdown and push it to GitHub and it'll be live on the site within 
a minute or two. Very exciting. 

#### Todo list

+ Turn tags into a set
+ Put tags in ToC
+ Put vertical spacing in main post area of home page
+ Improve Markdown parser/Hiccup converter
    + Understand indented sub-bullets
    + Make links work correctly
    + Use mono font for `code` and ```code blocks```
+ Dark mode (button or permanent?)
