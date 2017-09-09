var Generator = require('yeoman-generator');

module.exports = class extends Generator {
// The name `constructor` is important here
    
    constructor(args, opts) {
        // Calling the super constructor is important so our generator is correctly set up
        super(args, opts);

        // This makes `appname` a required argument.
        this.argument('appname', { type: String, required: true });

        // And you can then access it later; e.g.
        this.log(this.options.appname);
    }
};