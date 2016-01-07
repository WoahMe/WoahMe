namespace WoahMe.Data
{
    using Microsoft.AspNet.Identity.EntityFramework;
    using Models;
    using System.Data.Entity;

    public class WoahMeDbContext : IdentityDbContext<User>, IWoahMeDbContext
    {
        public WoahMeDbContext()
            : base("DefaultConnection", throwIfV1Schema: false)
        {
        }

        public static WoahMeDbContext Create()
        {
            return new WoahMeDbContext();
        }
    }
}
