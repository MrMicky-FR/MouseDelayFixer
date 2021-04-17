# MouseDelayFixer

A small tool for Minecraft 1.8-1.10 to fix without Forge [the mouse delay bug](https://bugs.mojang.com/browse/MC-67665) introduced in Minecraft 1.8 by injecting the fix in the client with a tweaker.

This fix is based on [MouseDelayFix by prplz](https://github.com/prplz/MouseDelayFix/).

This bug was fixed in Minecraft 1.11 so this tool has no effects on higher versions.

## How to use

This is an experimental project, but if you want to try it you can manually install it by modifying the JSON file of your Minecraft version (in `%AppData%` on Windows):
1. In `libraries` add this:
    ```json
     {
      "name": "fr.mrmicky:mousedelayfixer:1.0.0",
      "downloads": {
        "artifact": {
          "path": "fr/mrmicky/mousedelayfixer/1.0.0/mousedelayfixer-1.0.0.jar",
          "url": "https://github.com/MrMicky-FR/MouseDelayFixer/releases/download/v1.0.0/mousedelayfixer-1.0.0.jar",
          "sha1": "aee2153ab74ce682da53fed283bdbf4a02d12200",
          "size": 184351
        }
      }
    }
    ```
1. At the end of `minecraftArguments` add ` --tweakClass fr.mrmicky.mousedelayfixer.Tweaker`.
