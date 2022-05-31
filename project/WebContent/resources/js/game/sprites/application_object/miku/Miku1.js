// 피아식별 기능 추가

import Friendly from "../../sprite_rule/interaction_sprites/unit/friendly/Friendly.js";
import Collision from "../../sprite_rule/Collision.js";

const Miku1Animation = {
    'default' : [await getImage('/resources/image/miku/mikuStandTemporary1.png'), await getImage('/resources/image/miku/mikuStandTemporary2.png')],
    'moveRight' : [await getImage('/resources/image/miku/move/mikuStandTemporaryBehind1.png'), 
    			   await getImage('/resources/image/miku/move/mikuStandTemporaryBehind2.png'),
    			   await getImage('/resources/image/miku/move/mikuStandTemporaryBehind3.png'),
    			   await getImage('/resources/image/miku/move/mikuStandTemporaryBehind4.png')],
}


export default class Miku1 extends Friendly {
    constructor(){
        super(400,400, [new Collision([{x:-50, y:-100}, {x:50, y:0}])], Miku1Animation, 100, 40, 100, [50, 50])
    }
}