import generalCheckingSrc from "../assets/images/effect/general_checking.png";
import winnerSrc from "../assets/images/effect/winner.png";
import loserSrc from "../assets/images/effect/loser.png";

const generalChecking = new Image();
generalChecking.src = generalCheckingSrc;

const winner = new Image();
winner.src = winnerSrc;

const loser = new Image();
loser.src = loserSrc;

const effectImageLoader = {
    generalChecking,
    winner,
    loser
};

export default effectImageLoader;