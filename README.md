[![Discord](https://img.shields.io/discord/899151012290498620.svg?label=discord&logo=discord)](https://discord.petrus.dev/)

<p align="center">
  <img src="icon/console-icon.png" />
</p>

# FinalConsoleFilter

A plugin de that handles console filtering. This plugin can filter both Forge and Bukkit logs.

### Requirements

* This plugin requires [EverNifeCore](https://github.com/evernife/EverNifeCore)

### How to Use

For each logged line the plugin tries to apply the regex rules you have created, if there is a mach than the log is omitted from the console.

*If you don't know what is REGEX, search in the Google for the term.*

To add RegexRules to the plugin you can either edit the `config.yml` or use the ingame commands.

```
config.yml


HidePatterns:
  RegexList:
  - '.*(?i)abra CaDaBra(?-i).*'
```

The above example would hide any log line that matches de regex `.*(?i)abra CaDaBra(?-i).*`, in this case, any line that contains the phrase **abra cadabra** *(ignoreCase)*

You can also manage these rules using the commands bellow:

```
/finalconsolefilter add <regex>
/finalconsolefilter remove <regex>
/finalconsolefilter list
/finalconsolefilter reload
```

The permissions for the plugin can be found under [PermissionNodes](src/main/java/br/com/finalcraft/finalconsolefilter/PermissionNodes.java)