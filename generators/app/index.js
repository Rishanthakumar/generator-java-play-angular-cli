var Generator = require('yeoman-generator');
var fs = require('fs');

module.exports = class extends Generator {
    
    constructor(args, opts) {
        super(args, opts);
    }

    prompting() {
        return this.prompt([{
            type : 'input',
            name : 'projectName',
            message : 'Your project name?',
            default : this.appname
        }]).then((answers) => {
            this.appname = answers.projectName;
        });
    }

    writing() {
        this.fs.copyTpl(
            this.templatePath('**/*'),
            this.destinationPath(this.appname),
            { title: 'Templating with Yeoman' }
        );
    }

    install() {
        process.chdir(this.destinationPath(this.appname) + "/ui");
        this.spawnCommandSync('npm', ['i']);
        process.chdir(this.destinationPath(this.appname));
        this.spawnCommandSync('sbt', ['run']);
    }
};