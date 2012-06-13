<h1>MineQuest</h1>
**What is MineQuest?**
MineQuest is a full featured MMORPG system. It allows custom quests, classes, and more!

**What makes this different than other quest plugins?** It includes leveling capabilities, SpoutPlugin support, and the ability to create instanced dungeons.

    This is MineQuest, version 1.
    If you were looking for MineQuest on MC 1.1-R4 or before, see MineQuest, version 0 (MineQuest-0).

<hr/>

    The code in this repository has been marked **stable** as of 1.2.5 Milestone 5.
[![Build Status](http://build.lincomlinux.org/jenkins/job/MineQuest-Core/badge/icon)](http://build.lincomlinux.org/jenkins/job/MineQuest-Core/)
    
<h3>License</h3>
Copyright (c) 2011-2012, The MineQuest Team <http://www.theminequest.com/>

MineQuest is released under the terms of the **GNU General Public License, version 3**.
See the included _LICENSE.txt_ for details.

<h3>Team</h3>
MineQuest is developed by Ltan, JMonk, Echobob, cloned, TheWaffleGod, and robxu9.

<h3>Source</h3>
MineQuest uses Maven to manage and build its source. To checkout and build this project, use Maven.

Eclipse has some handy guides to using m2e - Google is your friend.

<h3>Forking/Pulling</h3>
* Please follow the general Java coding standards.
* Creating a new event? See the **QEvent** class.
* Implementing a new command? See the **CommandFrontend** class.
* Want to add a command to the _/minequest_ menu? Checkout the **CommandListener** class.
* Frontends should always use methods found in com.theminequest.MineQuest.Backend, and should **NEVER** call managers directly.
* Have a new attribute for the quest file? The **QuestParser.QHandler** interface can help with that.
* Utilities are available in the com.theminequest.MineQuest.Utils package.

Feel free to fork and submit pull requests to this repository. Please remember that by submitting your code you hereby release it under the project's license.

Abilities and Additional Events do **not** go in this repository; see _Minequest-1-Events_ and _Minequest-1-Abilities_.    