// 피아식별 기능 추가

import Unit from "../Unit.js";
import DefaultValue from "../../../../../../constant/DefaultValue.js";

export default class Friendly extends Unit {
    constructor(x,y,collisionList, animationImageList, hp, speed, maxSpeed, size){
        super(x,y,collisionList, animationImageList, hp, speed, maxSpeed, size)
        this.isNoHitTime = false
        this.isHit = false

        this.type.push('Friendly')
    }

    onCollisionEnter = (sprite, collision)=>{
        if(sprite.type.includes('Enemy')){
            this.underAttack(sprite)
        }
        this.customOnCollisionEnter(sprite, collision)
    }

    underAttack = (sprite)=>{
        this.hp--
        this.isNoHitTime = true
        this.isHit = true

        window.uiSettingFunc()

        clearTimeout(this.noHitTimer)
        clearTimeout(this.hitTimer)

        this.hitTimer = setTimeout(()=>{
            this.isHit = false
        }, DefaultValue.hitAnimationTime) 

        this.noHitTimer = setTimeout(()=>{
            this.isNoHitTime = false
        }, DefaultValue.noHitTime) 
        
        if(this.hp <= 0){
            this.die()
        }
    }

    die = () => {
        this.animationTypeChange('die')
        this.stopMoving()
        this.xForce = 0
        this.yForce = 0
        this.isNoHitTime = true
        this.isDie = true

        setTimeout(()=>{
            this.deleted = true
        }, 500)

        this.custemReset.bind(this)()
    }

    custemReset() {
        
    }
}