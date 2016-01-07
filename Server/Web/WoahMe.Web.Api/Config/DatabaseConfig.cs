namespace WoahMe.Web.Api
{
    using WoahMe.Data;
    using WoahMe.Data.Migrations;
    using System.Data.Entity;

    public static class DatabaseConfig
    {
        public static void Initialize()
        {
            Database.SetInitializer(new MigrateDatabaseToLatestVersion<WoahMeDbContext, Configuration>());
            WoahMeDbContext.Create().Database.Initialize(true);
        }
    }
}
