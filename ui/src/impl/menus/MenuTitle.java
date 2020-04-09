package impl.menus;

public class MenuTitle {
    private String titleName;

    MenuTitle(String title) {
        this.titleName = title;
    }

    public String getTitleName() {
        return titleName;
    }

    public void execute() {
        System.out.print(titleHorizontalLine() + titleContents() + titleHorizontalLine());
    }

    @Override
    public String toString() {
        return titleName;
    }

    private String titleHorizontalLine() {
        StringBuilder builder = new StringBuilder(Constants.MENU_WIDTH);
        builder.append(Constants.TITLE_EDGE);
        for (int i = 0; i < Constants.MENU_WIDTH - 2; i ++) {
            builder.append(Constants.TITLE_HLINE);
        }
        builder.append(Constants.TITLE_EDGE);
        builder.append("\r\n");

        return builder.toString();
    }

    private String titleContents() {
        StringBuilder builder = new StringBuilder();

        builder.append(Constants.TITLE_VLINE);
        for (int i = 0; i < (Constants.MENU_WIDTH - 2 - titleName.length()) / 2; i++) {
            builder.append(Constants.TITLE_BLANK);
        }
        builder.append(titleName);
        for (int i = 0; i < (Constants.MENU_WIDTH - 2 - titleName.length()) / 2; i++) {
            builder.append(Constants.TITLE_BLANK);
        }
        builder.append(Constants.TITLE_VLINE);
        builder.append("\r\n");

        return builder.toString();
    }

    /*@Override
    public String toString() {
        return titleHorizontalLine() + titleContents() + titleHorizontalLine();
    }*/
}