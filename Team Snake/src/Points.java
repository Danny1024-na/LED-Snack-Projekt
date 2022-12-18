
public class Points {
	private int x;
	private int y;
	private int[] color;
	
	public Points(int x,int y,int[] color)
	{
		this.x=x;
		this.y=y;
		this.color=color;
	}
	
	protected void SetXPosition(int x)
	{
		this.x=x;
	}
	protected void SetYPosition(int y)
	{
		this.y=y;
	}
	protected void SetColor(int r,int g,int b)
	{
		this.color=new int[]{r,g,b};
	}
	
	protected int getXPosition()
	{
		return x;
	}
	
	protected int getYPosition()
	{
		return y;
	}
	protected int[] getColor()
	{
		return color;
	}
	

}
