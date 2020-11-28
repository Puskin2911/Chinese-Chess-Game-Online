import BChariot from "../assets/images/pieces/3d/BChariot.png";
import BHorse from "../assets/images/pieces/3d/BHorse.png";
import BElephant from "../assets/images/pieces/3d/BElephant.png";
import BGeneral from "../assets/images/pieces/3d/BGeneral.png";
import BAdvisor from "../assets/images/pieces/3d/BAdvisor.png";
import BCannon from "../assets/images/pieces/3d/BCannon.png";
import BSoldier from "../assets/images/pieces/3d/BSoldier.png";
import RChariot from "../assets/images/pieces/3d/RChariot.png";
import RHorse from "../assets/images/pieces/3d/RHorse.png";
import RElephant from "../assets/images/pieces/3d/RElephant.png";
import RGeneral from "../assets/images/pieces/3d/RGeneral.png";
import RAdvisor from "../assets/images/pieces/3d/RAdvisor.png";
import RCannon from "../assets/images/pieces/3d/RCannon.png";
import RSoldier from "../assets/images/pieces/3d/RSoldier.png";

const imagePieceMap = new Map();

imagePieceMap.set('bch', createImageFromSource(BChariot));
imagePieceMap.set('bho', createImageFromSource(BHorse));
imagePieceMap.set('bel', createImageFromSource(BElephant));
imagePieceMap.set('bge', createImageFromSource(BGeneral));
imagePieceMap.set('bad', createImageFromSource(BAdvisor));
imagePieceMap.set('bca', createImageFromSource(BCannon));
imagePieceMap.set('bso', createImageFromSource(BSoldier));

// Red pieces
imagePieceMap.set('rch', createImageFromSource(RChariot));
imagePieceMap.set('rho', createImageFromSource(RHorse));
imagePieceMap.set('rel', createImageFromSource(RElephant));
imagePieceMap.set('rge', createImageFromSource(RGeneral));
imagePieceMap.set('rad', createImageFromSource(RAdvisor));
imagePieceMap.set('rca', createImageFromSource(RCannon));
imagePieceMap.set('rso', createImageFromSource(RSoldier));

function createImageFromSource(source) {
    const image = new Image();
    image.src = source;
    return image;
}

export default imagePieceMap;