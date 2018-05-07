package regexpr.identify;

public class REext extends RE {
	RE base;
	REext(RE re){
		base=re;
	}
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        REext other = (REext) obj;
        if(base.wh!=other.base.wh)return false;
        if(base.wh==2 && base.ch!=other.base.ch)return false;
        if(base.wh>2 && !(new REext(base.fst)).equals(new REext(other.base.fst)))return false;
        if((base.wh==3 ||base.wh==4) && !(new REext(base.snd)).equals(new REext(other.base.snd)))return false;
        return true;
	}
	@Override
	public String toString(){
		return base.toString();
	}
}
