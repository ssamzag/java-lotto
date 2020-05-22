package lotto.application;

import lotto.domain.lotto.LottoNumber;
import lotto.domain.lotto.LottoTicket;
import lotto.domain.lotto.LottoTickets;
import lotto.domain.rank.RankCalculator;
import lotto.domain.rank.Ranks;
import lotto.domain.shop.Money;
import lotto.domain.shop.Shop;

public class LottoService {
    public LottoTickets buyLottoTickets(Money money) {
        return Shop.sellLottoTickets(money);
    }

    public Ranks calculateRank(
            LottoTicket winTicket, LottoNumber winBonusNumber, LottoTickets lottoTickets
    ) {
        RankCalculator rankCalculator = new RankCalculator(winTicket, winBonusNumber);
        return lottoTickets.calculateRanks(rankCalculator);
    }
}
