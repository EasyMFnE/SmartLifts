<center>![SmartLifts](http://www.easymfne.net/images/smartlifts.png)</center>

<center>[Source](https://github.com/EasyMFnE/SmartLifts) |
[Change Log](https://github.com/EasyMFnE/SmartLifts/blob/master/CHANGES.log) |
[Feature Request](https://github.com/EasyMFnE/SmartLifts/issues) |
[Bug Report](https://github.com/EasyMFnE/SmartLifts/issues) |
[Donate](https://www.paypal.com/cgi-bin/webscr?hosted_button_id=457RX2KYUDY5G&item_name=SmartLifts&cmd=_s-xclick)</center>

<center>**Latest Version:** v1.0 for Bukkit 1.7+</center>

## About ##

SmartLifts is designed to allow players to create Lift signs that instantly teleport users up or down.  This plugin is highly configurable, allowing full control over how it can or cannot be used by players.  This plugin was originally created in order to have a Lift system that could be set up to disallow travel through Obsidian blocks on Raiding servers, but can be useful for servers of all types.

## Features ##

* Simple to create Lift signs that can teleport players vertically.
* Individual permissions for creating and using Lifts.
* Fully configurable messages and strings for localization.
* Can play sounds announcing Lift arrivals
* Configurable maximum range (in blocks)
* Configurable blacklist of blocks that cannot be traveled through.

## Installation ##

1. Download SmartLifts jar file.
2. Move/copy to your server's `plugins` folder.
3. Restart your server.
4. [**Optional**] Grant specific user permissions (see below).

## Permissions ##

SmartLifts utilizes three permission nodes:

* `smartlifts.command.smartlifts` - Allow user to use plugin's command (default: `op`)
* `smartlifts.lift.make` - Allow user to make Lift signs (default: `op`)
* `smartlifts.lift.use` - Allow user to use Lift signs (default: `true`)

## Commands ##

SmartLifts has only one command:

* `/smartlifts reload` - Reload configuration from disk.

## Configuration ##

At startup, the plugin will create a default configuration file if none exists.  This file is saved as `config.yml` and is located in `plugins/SmartLifts`. This file contains the following nodes:

        strings:
          liftText: (What the top line of a Lift sign should be)
          up: (What should be on the second line to go up)
          down: (What should be on the second line to go down)
          blocked: (Error message when lift path is blocked)
          unfound: (Error message when no destination was found)
          range: (Error message when destination is out of range)
          permission: (Error message when lacking 'make' permissions)
        
        sound:
          enabled: (boolean, whether sounds are played upon Lift use)
          use: (Name of sound from org.bukkit.Sound when Lift is used)
          fail: (Name of sound from org.bukkit.Sound when Lift fails)
        
        range: (integer, maximum range of Lifts in blocks) 
        
        blocked: (Blocks from org.bukkit.Material that are impassable)
          - obsidian
          - lava
          (...)

## Bugs/Requests ##

This template is continually tested to ensure that it is correct, but sometimes bugs can sneak in.  If you have found a bug within the project, or if you have a feature request, please [create an issue on Github](https://github.com/EasyMFnE/SmartLifts/issues).

## Donations ##

Donating is a great way to thank the developer if you find the plugin useful for your server, and encourages work on more 100% free and open-source plugins.  If you would like to donate (any amount), there is an easily accessible link in the top right corner of this page.  Thank you!

## Privacy ##

This plugin utilizes Hidendra's **Plugin-Metrics** system.  Users may opt out of this service by editing their configuration located in `plugins/Plugin Metrics`.  The following anonymous data is collected and sent to [mcstats.org](http://mcstats.org):

* A unique identifier
* The server's version of Java
* Whether the server is in online or offline mode
* The plugin's version
* The server's version
* The OS version, name, and architecture
* The number of CPU cores
* The number of online players
* The Metrics version

## License ##

This template is released as a free and open-source project under the [GNU General Public License version 3 (GPLv3)](http://www.gnu.org/copyleft/gpl.html).  To learn more about what this means, click that link or [read about it on Wikipedia](http://en.wikipedia.org/wiki/GNU_General_Public_License).
