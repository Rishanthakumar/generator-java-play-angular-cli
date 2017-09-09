var Generator = require('yeoman-generator');

module.exports = class extends Generator {
    
    constructor(args, opts) {
        super(args, opts);
    }

    prompting() {
        return this.prompt([{
            type : 'input',
            name : 'name',
            message : 'Your project name?',
            default : this.appname
        }]);
    }
};