package api.menus.components;

public class MenuTitle {

    private String titleName;

    public MenuTitle(String title) {
        this.titleName = title;
    }

    public String getTitleName() {
        return titleName;
    }

    public void show() {
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
            builder.append(Constants.TITLE_FILL);
        }
        builder.append(titleName);
        for (int i = 0; i < (Constants.MENU_WIDTH - 2 - titleName.length()) / 2; i++) {
            builder.append(Constants.TITLE_FILL);
        }
        builder.append(Constants.TITLE_VLINE);
        builder.append("\r\n");

        return builder.toString();
    }
}