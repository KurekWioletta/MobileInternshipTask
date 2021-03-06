# Github Client
![](https://i.imgur.com/mRyVTEd.jpg) |  ![](https://i.imgur.com/MCpVsnU.jpg) |   ![](https://i.imgur.com/Pykwohl.jpg) | 
:-----------------------------------:|:-------------------------------------:|:---------------------------------------:

This repository contains my Github client app which connects to public Github REST API and implements MVP architecture using Dagger2, Retrofit and RxJava. I provided unit and UI tests for presenter and view classes.

I changed the approach to dependency injection after reading an article about [Activities Subcomponents Multibinding](https://medium.com/azimolabs/activities-subcomponents-multibinding-in-dagger-2-85d6053d6a95).

#### Contained app packages:
  - **data:** data models and Github API manager
  - **di:** provided dependencies
  - **ui:** view and presenter classes with contract interfaces
  - **utils:** utility-helper classes
