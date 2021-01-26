## Post #3
#### 2021-01-26
###### meta technical

This post will be short, but not because of a lack of updates to the 
technical workings of the blog! I perhaps should've made smaller, more 
frequent posts, but I got very excited and motivated working on the things 
I'm about to describe.

First of all, I'm now writing this in a Markdown file which my IDE renders 
in live time, which is a much nicer experience that writing prose into a 
vector in the body of Clojure code. When the `generate-index-html!` function 
is called, it iterates through all the markdown blog post files in this 
folder, does some woefully incomplete parsing of the Markdown and converts each 
post into a Clojure hashmap with some metadata. The function then turns that 
vector of blog post hashmaps into Hiccup code and puts the posts in the main 
section of the webpage.

I decided to read all the Markdown posts into a vector of blog post 
hashmaps so that I could use it for multiple things on the website. For one, it
is used to populate the Table of Contents. It is also used to create unique 
pages for each post, which I'll soon be linking to in the Table of Contents.

I think I'll split the Github Actions deployment description into a separate 
post for now.
