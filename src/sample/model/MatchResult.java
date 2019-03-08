package sample.model;

/**
 * @Author: haitian
 * @Date: 2019/3/7 10:59 PM
 * @Description:
 */
public class MatchResult {

    public int matchCount = 0;
    public String sourceStr = "";

    public MatchResult(int matchCount, String sourceStr) {
        this.matchCount = matchCount;
        this.sourceStr = sourceStr;
    }
}
