# Database

This package separates the domain from how the databases are implemented.
If any changes in the database format/layout are conducted, the
domain entities and repositories will not be modified as many
items here are postgres and jpa specific