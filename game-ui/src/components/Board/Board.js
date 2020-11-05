import React from "react";

class Board extends React.Component {
    constructor(props) {
        super(props);
        this.setState({
            boardStatus: "'00bch_01bho_02bel_03bad_04bge_05bad_06bel_07bho_08bch'\n" +
                "            + '_10000_11000_12000_13000_14000_15000_16000_17000_18000'\n" +
                "            + '_20000_21bca_22000_23000_24000_25000_26000_27bca_28000'\n" +
                "            + '_30bso_31000_32bso_33000_34bso_35000_36bso_37000_38bso'\n" +
                "            + '_40000_41000_42000_43000_44000_45000_46000_47000_48000'\n" +
                "            + '_50000_51000_52000_53000_54000_55000_56000_57000_58000'\n" +
                "            + '_60rso_61000_62rso_63000_64rso_65000_66rso_67000_68rso'\n" +
                "            + '_70000_71rca_72000_73000_74000_75000_76000_77rca_78000'\n" +
                "            + '_80000_81000_82000_83000_84000_85000_86000_87000_88000'\n" +
                "            + '_90rch_91rho_92rel_93rad_94rge_95rad_96rel_97rho_98rch';"
        });
        this.setImage();
    }

    setImage(){
        //Black
        const bch = this.readImage('BChariot.png');
        const bho = this.readImage('BHorse.png');
        const bel = this.readImage('BElephant.png');
        const bge = this.readImage('BGeneral.png');
        const bad = this.readImage('BAdvisor.png');
        const bca = this.readImage('BCannon.png');
        const bso = this.readImage('BSoldier.png');
        //Red
        const rch = this.readImage('RChariot.png');
        const rho = this.readImage('RHorse.png');
        const rel = this.readImage('RElephant.png');
        const rge = this.readImage('RGeneral.png');
        const rad = this.readImage('RAdvisor.png');
        const rca = this.readImage('RCannon.png');
        const rso = this.readImage('RSoldier.png');

        return [
            ['bch', bch],
            ['bho', bho],
            ['bel', bel],
            ['bge', bge],
            ['bad', bad],
            ['bca', bca],
            ['bso', bso],
            ['rch', rch],
            ['rho', rho],
            ['rel', rel],
            ['rge', rge],
            ['rad', rad],
            ['rca', rca],
            ['rso', rso]
        ]
    }

    readImage(name) {
        const image = new Image();
        image.src = '/img/3d/' + name;
        return image;
    }

    render() {
        return (
            <canvas id="cc-board" className="border border-success">
            </canvas>
        )
    }
}

export default Board;