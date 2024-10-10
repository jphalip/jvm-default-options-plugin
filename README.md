<!-- Plugin description -->
This IntelliJ plugin allows you to set default values for JVM options and environment variables
when running Java-based configurations. The values can be scoped both globally and to a
specific project.

To download and install the plugin, visit: https://plugins.jetbrains.com/plugin/21136-jvm-default-options

To set the default values, open the `Build, Execution, Deployment` → `JVM Default Options` panel
in the IDE settings.

You can set two types of default values:

* VM options, for example:
  
  `-Dmy.favorite.property=abcd -Danother.property=helloworld`

* Environment variables, for example:

  `MY_VAR=abcd MY_OTHER_VAR=1234`

Values set in a run configuration take precedence over both global and project default
values. Project default values take precedence over global default values.
<!-- Plugin description end -->