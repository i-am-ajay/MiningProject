const dimension_bar = {'width':300, 'height':160};
const margin = {'left':30, 'top': 20, 'right':20,'bottom':20 }
var categorical_bar = [
	  { "name" : "schemeAccent", "n": 8},
	  { "name" : "schemeDark2", "n": 8},
	  { "name" : "schemePastel2", "n": 8},
	  { "name" : "schemeSet2", "n": 8},
	  { "name" : "schemeSet1", "n": 9},
	  { "name" : "schemePastel1", "n": 9},
	  { "name" : "schemeCategory10", "n" : 10},
	  { "name" : "schemeSet3", "n" : 12 },
	  { "name" : "schemePaired", "n": 12},
	  { "name" : "schemeCategory20", "n" : 20 },
	  { "name" : "schemeCategory20b", "n" : 20},
	  { "name" : "schemeCategory20c", "n" : 20 }
	]

const svg_bar = d3.select('.canvas_bar')
                .append('svg')
                .attr('width', dimension_bar.width + 50)
                .attr('height',dimension_bar.height+50);


const graph_bar = svg_bar.append('g')
					.attr('width',dimension_bar.width)
					.attr('height',dimension_bar.height);
graph_bar.attr('transform',`translate(${margin.left},${margin.top})`)

// create group for axis.
const xAxisGroup_bar = graph_bar.append('g');
const yAxisGroup_bar = graph_bar.append('g');
// add tweens to you chat.

xAxisGroup_bar.attr('transform',`translate(0,${dimension_bar.height})`);

// create tweens

                
// update function
const update_bar = data => {
    // set a scale
    const linear_bar = d3.scaleLinear();
    linear_bar.domain([0,100])
    .range([160,0]);
    
    const band_x = d3.scaleBand();
    band_x
    	.domain(data.map(d =>d.name))
    	.range([0,300])
    	.padding(0.3);
    
    const width_tween = d =>{
    	const i = d3.interpolate(0, band_x.bandwidth());
    	return t => i(t);
    }
    
    // generate axis
    const xAxis_bar = d3.axisBottom(band_x);
    const yAxis_bar = d3.axisLeft(linear_bar);
    // Add Y ticks
    yAxis_bar.ticks(4);
    
    // Add x ticks
    
    // combine axis and groups 
    xAxisGroup_bar.call(xAxis_bar);
    yAxisGroup_bar.call(yAxis_bar);
    
    const rects = graph_bar.selectAll('rect').data(data);
    
    // remove present dom.
    rects.exit().remove();
    // update existing graphs
   rects.attr('width',band_x.bandwidth)
    	.attr('x',(d) => band_x(d.name))
    	.attr('y',d => linear_bar(d.value))
    	.attr('height',d => dimension_bar.height- linear_bar(d.value));
    
    // update 
 
    // enter new dom.
    rects.enter().append('rect')
    	.attr('x',d => band_x(d.name))
    	.attr('fill','orange')
    	.attr('y',dimension_bar.height)
    	.transition().duration(750)
    		.attr('y',d =>linear_bar(d.value))
    		.attr('height',d => dimension_bar.height - linear_bar(d.value))
    		.attrTween('width',width_tween);
}