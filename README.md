[![Discord](https://img.shields.io/discord/899151012290498620.svg?label=discord&logo=discord)](https://discord.petrus.dev/)

<p align="center">
  <img src="icon/console-icon.png" />
</p>

# FinalConsoleFilter

A plugin de that handles console filtering. This plugin can hide configured messages or errors from server console and logs.

### Requirements

* This plugin requires [EverNifeCore](https://github.com/evernife/EverNifeCore)

### How to Use

For each logged line the plugin tries to apply the regex rules you have created, if there is a mach than the log is omitted from the console.

*If you don't know what is REGEX, search in the Google for the term.*

To add RegexRules to the plugin you can either edit the `config.yml` or use the ingame commands.

```
config.yml


HidePatterns:
  ContainsList:
  - 'abra cadabra'
  - '/tpa'
  RegexList:
  - '.*11.*12.*'
```

The above example would hide 3 types of lines.
 - ***1:*** Hide Any line that contains the phrase **abra cadabra** *(ignoreCase)*
 - ***2:*** Hide Any line that contains the phrase **/tpa** *(ignoreCase)*
 - ***3:*** Hide Any line that contains the numbers **11**%ANYTHING_BETWEEN_THEM%**12** *(ignoreCase)*

The RegexList is intended for more experienced users, if you are not one of them, use only the "ContainsList", its easy and simple!

You can also manage these rules using the commands bellow:

```
/finalconsolefilter add <text>
/finalconsolefilter addRegex <regex>
/finalconsolefilter remove <text|regex>
/finalconsolefilter list
/finalconsolefilter reload
```

The permissions for the plugin can be found under [PermissionNodes](src/main/java/br/com/finalcraft/finalconsolefilter/PermissionNodes.java)