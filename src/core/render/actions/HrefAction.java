package core.render.actions;

public class HrefAction implements Action {

    public enum Target {
        _replace,
        _self,
        _parent,
        _top,
        _blank
    }


    private String link;
    private Target target;

    public String getLink() {
        return link;
    }

    public Target getTarget() {
        return target;
    }

    public HrefAction(String link, Target target) {
        this.link = link;
        this.target = target;
    }
}
