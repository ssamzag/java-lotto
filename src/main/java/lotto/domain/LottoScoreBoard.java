package lotto.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LottoScoreBoard {

  private static final int INIT_VALUE = 0;

  private final Map<String, Integer> matchResultMap;
  private final Money money;

  public LottoScoreBoard(Map<String, Integer> matchResultMap, Money money) {
    this.matchResultMap = matchResultMap;
    this.money = money;
  }

  public static LottoScoreBoard createLottoResult(Money money,
      List<LottoRank> lottoMatchResult) {

    Map<String, Integer> resultMap = new HashMap<>();

    for (LottoRank result : lottoMatchResult) {
      initLottoRankWithPrize(resultMap, result);
    }

    return new LottoScoreBoard(resultMap, money);
  }

  public double totalEarningRate() {
    return money.calculateEarningRate(getTotalWinningMoney(matchResultMap));
  }

  public String toResultString() {
    return "3개 일치 (5000원) - " + matchResultMap.getOrDefault(LottoRank.FIFTH.name(), INIT_VALUE) + "개\n"
        + "4개 일치 (50000원) - " + matchResultMap.getOrDefault(LottoRank.FOURTH.name(), INIT_VALUE) + "개\n"
        + "5개 일치 (1500000원) - " + matchResultMap.getOrDefault(LottoRank.THIRD.name(), INIT_VALUE) + "개\n"
        + "5개 일치, 보너스 볼 일치(30000000원) - " + matchResultMap.getOrDefault(LottoRank.SECOND.name(), INIT_VALUE) + "개\n"
        + "6개 일치 (2000000000원) - " + matchResultMap.getOrDefault(LottoRank.FIRST.name(), INIT_VALUE) + "개";
  }

  private static void initLottoRankWithPrize(Map<String, Integer> resultMap, LottoRank rankResult) {
    if (!LottoRank.isNone(rankResult)) {
      putLottoMatchResult(resultMap, rankResult);
    }
  }

  private static void putLottoMatchResult(Map<String, Integer> earningBoard, LottoRank key) {
    earningBoard.put(key.name(),
        earningBoard.getOrDefault(key.name(), 0) + 1);
  }

  private int getTotalWinningMoney(Map<String, Integer> map) {
    return map.entrySet()
        .stream()
        .mapToInt(this::getWinningMoney)
        .sum();
  }

  private int getWinningMoney(Entry<String, Integer> entry) {
    return LottoRank.matchRankWinnerMoney(entry.getKey())
        .multiple(entry.getValue());
  }
}
