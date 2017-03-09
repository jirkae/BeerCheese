#Backend notes
##Manual steps on prod
* setup DB `CREATE SCHEMA `beer` CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;`
* setup file path for images (property `images.location.path`, if empty uses randomly generated temporary folder)
##Testing
* embedded db needs 33060 port