# chatchannels

## Description

chatchannels provides simple and configurable chat channels for Paper servers.
The plugin aims to be lightweight and easy to use.

## Configuration

View the [Default Configuration](https://github.com/MCMDEV/chatchannels/blob/main/src/main/resources/config.yml)
for more info on configuring the plugin.

## Commands

- `/chatchannels` - View the channel list menu.
- `/togglechannel <channel>` - Join or leave a channel.
- `/sendchannel <channel> <message>` - Send a message to a channel.
- `/switchchannel <channel>` - Switch the currently active channel.
- `/switchchannel` - Deactivate the currently active channel.

## Permissions

- `chatchannels` - Access to the `/chatchannels` command.
- `chatchannels.togglechannel` - Access to the `/togglechannel` command.
- `chatchannels.sendchannel` - Access to the `/sendchannel` command.
- `chatchannels.switchchannel` - Access to the `/switchchannel` command.
- `chatchannels.join.<channel>` - Access to join a channel.
- `chatchannels.autojoin.<channel>` - Automatically join a channel on login.

Only players with the permission to join a channel will see it in the list..