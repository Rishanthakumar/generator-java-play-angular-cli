# Angular Java Play Generator

![Angular Java Play Generator Image](https://raw.githubusercontent.com/Rishanthakumar/generator-java-play-angular-cli/master/images/generator-java-play-angular-cli.png)

Yeoman generator for creating Angular backed by Java Play - lets you quickly set up a project following best practices.

## Prerequisites

* [Node.js](https://nodejs.org/en/)
* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [SBT](https://www.scala-sbt.org/)

## Usage

Install `yo` and `generator-java-play-angular-cli`:

````
npm install -g yo generator-java-play-angular-cli
````

Run `yo java-play-angular-cli`

````
yo java-play-angular-cli
````
Go in to the project location and run `sbt run` to run both backend and frontend builds

````
sbt run
````

> The first time you run SBT, it may take a while to download all the dependencies it needs, but after that first run, it will download new dependencies only as needed.

## Contributors

[<img src="https://avatars0.githubusercontent.com/u/13392302?s=400&v=4" width="100px;"/><br /><sub>Rishanthakumar</sub>](https://github.com/Rishanthakumar)| [<img src="https://avatars0.githubusercontent.com/u/18510957?s=400&v=4" width="100px;"/><br /><sub>Saad Sahibjan</sub>](https://github.com/saadsahibjan)
| :---: | :---: |

## License

This software is licensed under the [MIT](https://github.com/Rishanthakumar/generator-java-play-angular-cli/blob/dev/LICENSE) license
