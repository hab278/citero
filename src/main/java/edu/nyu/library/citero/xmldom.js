/*
 * Copyright (c) 2009 and 2010 Frank G. Bennett, Jr. All Rights
 * Reserved.
 *
 * The contents of this file are subject to the Common Public
 * Attribution License Version 1.0 (the “License”); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://bitbucket.org/fbennett/citeproc-js/src/tip/LICENSE.
 *
 * The License is based on the Mozilla Public License Version 1.1 but
 * Sections 14 and 15 have been added to cover use of software over a
 * computer network and provide for limited attribution for the
 * Original Developer. In addition, Exhibit A has been modified to be
 * consistent with Exhibit B.
 *
 * Software distributed under the License is distributed on an “AS IS”
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is the citation formatting software known as
 * "citeproc-js" (an implementation of the Citation Style Language
 * [CSL]), including the original test fixtures and software located
 * under the ./std subdirectory of the distribution archive.
 *
 * The Original Developer is not the Initial Developer and is
 * __________. If left blank, the Original Developer is the Initial
 * Developer.
 *
 * The Initial Developer of the Original Code is Frank G. Bennett,
 * Jr. All portions of the code written by Frank G. Bennett, Jr. are
 * Copyright (c) 2009 and 2010 Frank G. Bennett, Jr. All Rights Reserved.
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the GNU Affero General Public License (the [AGPLv3]
 * License), in which case the provisions of [AGPLv3] License are
 * applicable instead of those above. If you wish to allow use of your
 * version of this file only under the terms of the [AGPLv3] License
 * and not to allow others to use your version of this file under the
 * CPAL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the
 * [AGPLv3] License. If you do not delete the provisions above, a
 * recipient may use your version of this file under either the CPAL
 * or the [AGPLv3] License.”
 */
var ActiveXObject;
var XMLHttpRequest;
var DOMParser;
var CSL_IS_IE;
var CSL_CHROME = function () {
	if ("undefined" == typeof DOMParser || CSL_IS_IE) {
		CSL_IS_IE = true;
		DOMParser = function() {};
		DOMParser.prototype.parseFromString = function(str, contentType) {
			if ("undefined" != typeof ActiveXObject) {
				var xmldata = new ActiveXObject('MSXML.DomDocument');
				xmldata.async = false;
				xmldata.loadXML(str);
				return xmldata;
			} else if ("undefined" != typeof XMLHttpRequest) {
				var xmldata = new XMLHttpRequest;
				if (!contentType) {
					contentType = 'text/xml';
				}
				xmldata.open('GET', 'data:' + contentType + ';charset=utf-8,' + encodeURIComponent(str), false);
				if(xmldata.overrideMimeType) {
					xmldata.overrideMimeType(contentType);
				}
				xmldata.send(null);
				return xmldata.responseXML;
			}
		};
		this.hasAttributes = function (node) {
			var ret;
			if (node.attributes && node.attributes.length) {
				ret = true;
			} else {
				ret = false;
			}
			return ret;
		};
	} else {
		this.hasAttributes = function (node) {
			return node["hasAttributes"]();
		};
	}
	this.importNode = function (doc, srcElement) {
		if ("undefined" == typeof doc.importNode) {
			var ret = this._importNode(doc, srcElement, true);
		} else {
			var ret = doc.importNode(srcElement, true);
		}
		return ret;
	};
	this._importNode = function(doc, node, allChildren) {
		switch (node.nodeType) {
			case 1:
				var newNode = doc.createElement(node.nodeName);
				if (node.attributes && node.attributes.length > 0)
					for (var i = 0, il = node.attributes.length; i < il;)
						newNode.setAttribute(node.attributes[i].nodeName, node.getAttribute(node.attributes[i++].nodeName));
					if (allChildren && node.childNodes && node.childNodes.length > 0)
						for (var i = 0, il = node.childNodes.length; i < il;)
							newNode.appendChild(this._importNode(doc, node.childNodes[i++], allChildren));
				return newNode;
				break;
			case 3:
			case 4:
			case 8:
				return doc.createTextNode(node.nodeValue);
				break;
		}
	};
	this.parser = new DOMParser();
	var inst_txt = "<docco><institution institution-parts=\"long\" delimiter=\", \" substitute-use-first=\"1\" use-last=\"1\"/></docco>";
	var inst_doc = this.parser.parseFromString(inst_txt, "text/xml");
	var inst_node = inst_doc.getElementsByTagName("institution");
	this.institution = inst_node.item(0);
	this.ns = "http://purl.org/net/xbiblio/csl";
};
CSL_CHROME.prototype.clean = function (xml) {
	xml = xml.replace(/<\?[^?]+\?>/g, "");
	xml = xml.replace(/<![^>]+>/g, "");
	xml = xml.replace(/^\s+/, "");
	xml = xml.replace(/\s+$/, "");
	xml = xml.replace(/^\n*/, "");
	return xml;
};
CSL_CHROME.prototype.children = function (myxml) {
	var children, pos, len, ret;
	if (myxml) {
		ret = [];
		children = myxml.childNodes;
		for (pos = 0, len = children.length; pos < len; pos += 1) {
			if (children[pos].nodeName != "#text") {
				ret.push(children[pos]);
			}
		}
		return ret;
	} else {
		return [];
	}
};
CSL_CHROME.prototype.nodename = function (myxml) {
	var ret = myxml.nodeName;
	return ret;
};
CSL_CHROME.prototype.attributes = function (myxml) {
	var ret, attrs, attr, key, xml, pos, len;
	ret = new Object();
	if (myxml && this.hasAttributes(myxml)) {
		attrs = myxml.attributes;
		for (pos = 0, len=attrs.length; pos < len; pos += 1) {
			attr = attrs[pos];
			ret["@" + attr.name] = attr.value;
		}
	}
	return ret;
};
CSL_CHROME.prototype.content = function (myxml) {
	var ret;
	if ("undefined" != typeof myxml.textContent) {
		ret = myxml.textContent;
	} else if ("undefined" != typeof myxml.innerText) {
		ret = myxml.innerText;
	} else {
		ret = myxml.txt;
	}
	return ret;
};
CSL_CHROME.prototype.namespace = {
	"xml":"http://www.w3.org/XML/1998/namespace"
}
CSL_CHROME.prototype.numberofnodes = function (myxml) {
	if (myxml) {
		return myxml.length;
	} else {
		return 0;
	}
};
CSL_CHROME.prototype.getAttributeName = function (attr) {
	var ret = attr.name;
	return ret;
}
CSL_CHROME.prototype.getAttributeValue = function (myxml,name,namespace) {
	var ret = "";
	if (myxml && this.hasAttributes(myxml) && myxml.getAttribute(name)) {
		ret = myxml.getAttribute(name);
	}
	return ret;
}
CSL_CHROME.prototype.getNodeValue = function (myxml,name) {
	var ret = "";
	if (name){
		var vals = myxml.getElementsByTagName(name);
		if (vals.length > 0) {
			if ("undefined" != typeof vals[0].textContent) {
				ret = vals[0].textContent;
			} else if ("undefined" != typeof vals[0].innerText) {
				ret = vals[0].innerText;
			} else {
				ret = vals[0].text;
			}
		}
	} else {
		ret = myxml;
	}
	if (ret && ret.childNodes && (ret.childNodes.length == 0 || (ret.childNodes.length == 1 && ret.firstChild.nodeName == "#text"))) {
		if ("undefined" != typeof ret.textContent) {
			ret = ret.textContent;
		} else if ("undefined" != typeof ret.innerText) {
			ret = ret.innerText;
		} else {
			ret = ret.text;
		}
	}
	return ret;
}
CSL_CHROME.prototype.setAttributeOnNodeIdentifiedByNameAttribute = function (myxml,nodename,partname,attrname,val) {
	var pos, len, xml, nodes, node;
	if (attrname.slice(0,1) === '@'){
		attrname = attrname.slice(1);
	}
	nodes = myxml.getElementsByTagName(nodename);
	for (pos = 0, len = nodes.length; pos < len; pos += 1) {
		node = nodes[pos];
		if (node.getAttribute("name") != partname) {
			continue;
		}
		node.setAttribute(attrname, val);
	}
}
CSL_CHROME.prototype.deleteNodeByNameAttribute = function (myxml,val) {
	var pos, len, node, nodes;
	nodes = myxml.childNodes;
	for (pos = 0, len = nodes.length; pos < len; pos += 1) {
		node = nodes[pos];
		if (!node || node.nodeType == node.TEXT_NODE) {
			continue;
		}
		if (this.hasAttributes(node) && node.getAttribute("name") == val) {
			myxml.removeChild(nodes[pos]);
		}
	}
}
CSL_CHROME.prototype.deleteAttribute = function (myxml,attr) {
	myxml.removeAttribute(attr);
}
CSL_CHROME.prototype.setAttribute = function (myxml,attr,val) {
	var attribute;
	if (!myxml.ownerDocument) {
		myxml = myxml.firstChild;
	}
	attribute = myxml.ownerDocument.createAttribute(attr);
	myxml.setAttribute(attr, val);
    return false;
}
CSL_CHROME.prototype.nodeCopy = function (myxml) {
	var cloned_node = myxml.cloneNode(true);
	return cloned_node;
}
CSL_CHROME.prototype.getNodesByName = function (myxml,name,nameattrval) {
	var ret, nodes, node, pos, len;
	ret = [];
	nodes = myxml.getElementsByTagName(name);
	for (pos = 0, len = nodes.length; pos < len; pos += 1) {
		node = nodes.item(pos);
		if (nameattrval && !(this.hasAttributes(node) && node.getAttribute("name") == nameattrval)) {
			continue;
		}
		ret.push(node);
	}
	return ret;
}
CSL_CHROME.prototype.nodeNameIs = function (myxml,name) {
	if (name == myxml.nodeName) {
		return true;
	}
	return false;
}
CSL_CHROME.prototype.makeXml = function (myxml) {
	var ret, topnode;
	if (!myxml) {
		myxml = "<docco><bogus/></docco>";
	}
	var nodetree = this.parser.parseFromString(myxml, "application/xml");
	return nodetree.firstChild;
};
CSL_CHROME.prototype.insertChildNodeAfter = function (parent,node,pos,datexml) {
	var myxml, xml;
	myxml = this.importNode(node.ownerDocument, datexml);
	parent.replaceChild(myxml, node);
 	return parent;
 };
CSL_CHROME.prototype.addInstitutionNodes = function(myxml) {
	var names, thenames, institution, theinstitution, name, thename, xml, pos, len;
	names = myxml.getElementsByTagName("names");
	for (pos = 0, len = names.length; pos < len; pos += 1) {
		thenames = names[pos];
		name = thenames.getElementsByTagName("name");
		if (name.length == 0) {
			continue;
		}
		institution = thenames.getElementsByTagName("institution");
		if (institution.length == 0) {
			theinstitution = this.importNode(myxml.ownerDocument, this.institution);
			thename = name[0];
			thenames.insertBefore(theinstitution, thename.nextSibling);
		}
	}
};
