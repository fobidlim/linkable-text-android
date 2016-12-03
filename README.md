# Linkable Text Library
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-linkable--text-green.svg?style=flat)](http://android-arsenal.com/details/1/4674)

[![Join the chat at https://gitter.im/fobid/linkable-text/Lobby](https://badges.gitter.im/fobid/linkable-text/Lobby.svg)](https://gitter.im/fobid/linkable-text?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Github Release][release-image]][release-url]

You can download Linkable Text Sample application on Google Play.

[![Get it on Google Play](http://www.android.com/images/brand/get_it_on_play_logo_small.png)](https://play.google.com/store/apps/details?id=com.github.fobid.linkabletext.sample)

# Download
Download [the latest JAR](https://repo1.maven.org/maven2/com/github/fobid/linkable-text/0.1.3/linkable-text-0.1.3.aar) or grab via Maven:
```
<dependency>
  <groupId>com.github.fobid</groupId>
  <artifactId>linkable-text</artifactId>
  <version>0.1.3</version>
</dependency>
```
or Gradle:
```
compile 'com.github.fobid:linkable-text:0.1.3'
```

# Usage
```
 <com.github.fobid.linkabletext.widget.LinkableTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```
If you don't want to set link, then add `app:enabledLinks="false"` in your xml.
Or, `LinkableTextView.setEnabledLinks(false)` in your code.

You must call `LinkableTextView.setOnLinkClickListener()` after `setText()` to set links  clickable.

# Attributes
```
<com.github.fobid.linkabletext.widget.LinkableTextView
		xmlns:linkable="http://schemas.android.com/apk/res-auto"
        android:id="@android:id/text1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        linkable:enabledLinks="false" />

```
You can make all links to be disabled with `enabledLinks=false` in your layout xml or `setEnabledLinks(false)` in your code.
Every links are able to be set disabled on each.

If you remove all of underlines, then use `enabledUnderlines=false` in your layout xml or `setEnabledUnderlines(false)` in your code.
It is also supported to each links.

Default value of all links and underlines is `true`

# License
```
Copyright 2016 Fobid

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[release-image]: https://img.shields.io/badge/release-v0.1.3-lightgrey.svg
[release-url]: https://github.com/fobid/linkable-text-android/releases/tag/v0.1.3
