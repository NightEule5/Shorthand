enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement()
{
	versionCatalogs()
	{
		val deps by creating()
		{
			from(files("versions.toml"))
		}
	}
}

rootProject.name = "shorthand"
