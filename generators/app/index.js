var Generator = require('yeoman-generator');

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
        this.fs.copy(
            this.templatePath('**/*'),
            this.destinationPath(this.appname),
            {
                title: 'Templating with Yeomen',
                globOptions: { dot: true }
            }
        );
    }

    install() {
        this.log('Installing NPM modules...');
        process.chdir(this.destinationPath(this.appname) + "/ui");
        this.spawnCommandSync('npm', ['i']);
    }
};