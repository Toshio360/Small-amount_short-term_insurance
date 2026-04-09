package com.example.toshio.demo.enums;

public enum ApplicationSteps {
    POLICYHOLDER(1, "/contract/policyholder","契約者情報"),
    INSURED(2, "/contract/insured","被保険者情報"),
    PRODUCT(3, "/contract/product","商品選択"),
    PLAN(4, "/contract/plan","プラン選択"),
    ELIGIBILITY(5, "/contract/eligibility","申込可否判定"),
    CONFIRM(6, "/contract/confirm","内容確認"),
    COMPLETE(7, "/contract/complete","完了");

    private final int order;
    private final String path;
    private final String displayName;
    ApplicationSteps(int order, String path, String displayName) {
        this.order = order;
        this.path = path;
        this.displayName = displayName;
    }
    public int getOrder() {
        return order;
    }
    public String getPath() {
        return path;
    }
    public String getDisplayName() {
        return this.order + ". " + this.displayName;
    }
}
