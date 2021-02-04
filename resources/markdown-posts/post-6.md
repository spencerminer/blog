## Notes from a Discussion on Handling Emergency Service Outages
#### 2021-02-04
###### software-design

I had a great conversation with a wise colleague from work in the aftermath 
of a small emergency software outage. 

#### How to handle an emergency outage

+ Don’t freak out when something bad happens
    + Being freaked out makes it harder to think
    + Taking action based on immediate reactions can cause poor decisions 
      that cause more issues
+ Don’t just rollback immediately
    + You will have lost your best resource for debugging - the system that 
      was actually failing
    + You can’t deploy again because things will break again
    + But you can’t fix the problem because you don’t know exactly what failed!
        + You will have to just guess at what caused the problem
+ Reproduce the error yourself to see what exactly is failing
    + Logs are just hearsay
        + You can’t double-check them
        + Circumstantial evidence
            + They usually collect behavior, give trends, but you don’t see 
              exactly how each function responds in each situation and you 
              can’t dig down usually
    + Circumstantial evidence is not good evidence, don’t let it lead you 
      down a false path
+ Once you can reproduce and know what the problem is, then you can rollback
+ Need an ability to quickly query the service to reproduce the error
    + Need it to be fast, easy to use, easy to modify
    + Postman can be good for this
        + Shared team workspace for saving queries we all use
        + Easily change details to look at more things
        + Easy to see what is happening, in case debugging is needed
    + Scripted curls can be really nice, but can also break and be unfixable
    + Writing scripts in clojure could be nice, but could have same problems as 
      scripts

#### Design for failures

+ Networked systems fail often, thus:
+ Designing for the failing case is as important, if not more, than 
  designing for the success case
+ Designing for failing case involves avoiding these problems
    + Looking like it failed but it actually succeeded (e.g. timeout/dropped 
      connection, but kept working and succeeded on other side)
    + Looking like it succeeded but it actually failed (rarer, but e.g. 
      nothing is returned at all and that’s assumed to be good)
    + Not knowing at all if something passed or failed (no return value), so 
      you need to check it with a separate call
+ Want things to fail loudly and fast
+ Resiliency - if it failed it should be able to try again safely, and do that

#### Todo list

+ ~~Turn tags into a set~~
+ ~~Put tags in ToC~~
+ Make ToC tags links
+ Put vertical spacing in main post area of home page
+ Improve Markdown parser/Hiccup converter
    + ~~Understand indented sub-bullets~~
    + Make links in text work correctly
    + Use mono font for `code` and ```code blocks```
+ Dark mode (button or permanent?)
