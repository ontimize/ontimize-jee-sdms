<!-- ## [Unreleased] -->
<!-- ### Added ✔️-->
<!-- ### Changed 🛠️-->
<!-- ### Deprecated 🛑-->
<!-- ### Removed 🗑️-->
<!-- ### Fixed 🐛-->
<!-- ### Security 🛡️-->
<!-- Este fichero sigue el formato de https://keepachangelog.com -->

## [Unreleased]

## [1.1.0] - 21/06/2023

### Added ✔️
* **New field on DMS files:** The system has been updated so that when a file is uploaded to the DMS, the creationDate field is added and when information is retrieved from the files, it is also retrieved.
* **Default valure to workspace default:** The default value `/{id}` has been added to the workspace **default**. Now if no workspace is set, these values are the defaults.

### Changed 🛠️
* **Code Format:** The code has been formatted.
* **Changelog**: The structure of the CHANGELOG.md file has been modified so that it follows the structure shown at [keepachangelog](https://keepachangelog.com/).

### Fixed 🐛
* **SdmsS3RepositoryDto:** Fixed a bug that occurred in some cases when extracting the prefix from the S3 key.
* **OSdmsS3RepositoryDto:** Fixed a bug in the implementation of the toMap method that caused the EntityResult to sometimes not return certain fields such as size when creating the EntityResult.

## [1.0.0] - 12/06/2023

### Added ✔️

* **Abstract Controller:** Created an abstract controller that adds endpoints and executes the corresponding methods of
  the DMS system for an entity.
* **S3 Engine:** Implemented an engine to manage the DMS system through the Amazon AWS S3 service API.
* **OSdmsEventListener Annotation:** Added an annotation to set event listeners for available events in the DMS system
  easily.
* **DMS Service Events:** Added an Enum with corresponding events for the DMS service.
* **S3 Repository Events:** Added an Enum with corresponding events for the S3 repository of the S3 engine.
* **OSdmsWorkspace Annotation:** Added an annotation to establish multiple query workspaces on the DMS system for an
  entity.

[unreleased]: https://github.com/ontimize/ontimize-jee-sdms/compare/1.1.0...HEAD
[1.1.0]: https://github.com/ontimize/ontimize-jee-sdms/compare/1.0.0...1.1.0
[1.0.0]: https://github.com/ontimize/ontimize-jee-sdms/releases/tag/1.0.0