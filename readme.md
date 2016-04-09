Pgyer TeamCity plugin
==============
 Teamcity Compat: 9.0+

 This is project is Pgyer TeamCity plugin, that uploading package to Pgyer server.

 1. Build
 Issue 'mvn package' command from the root project to build the plugin. Resulting package <artifactId>.zip will be placed in 'target' directory. 
 
 2. Install
 To install the plugin, put zip archive to 'plugins' dir under TeamCity data directory and restart the server.

###### Added Parameters:
  * `%build.changelog%`  The formatted changelog of VCS which used to the release notes.




## Author
    Ligboy Liu (ligboy@gmail.com)
## Contributers
    Roy Lee (947523859@qq.com)

## License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
