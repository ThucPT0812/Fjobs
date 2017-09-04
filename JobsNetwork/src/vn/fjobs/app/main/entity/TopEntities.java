package vn.fjobs.app.main.entity;

public class TopEntities {

    private String linkBanner;
    private String linkLogo;
    private String nameCompany;
    private String companyDes;

    public TopEntities(){

    }

    public TopEntities(String linkBanner, String linkLogo, String nameCompany, String companyDes){
        this.linkBanner = linkBanner;
        this.linkLogo = linkLogo;
        this.nameCompany = nameCompany;
        this.companyDes = companyDes;
    }

    public String getLinkBanner() {
        return linkBanner;
    }

    public void setLinkBanner(String linkBanner) {
        this.linkBanner = linkBanner;
    }

    public String getLinkLogo() {
        return linkLogo;
    }

    public void setLinkLogo(String linkLogo) {
        this.linkLogo = linkLogo;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getCompanyDes() {
        return companyDes;
    }

    public void setCompanyDes(String companyDes) {
        this.companyDes = companyDes;
    }
}
