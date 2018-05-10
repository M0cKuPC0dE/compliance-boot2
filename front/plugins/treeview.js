/* global $ */
if (process.BROWSER_BUILD) {
  var tree = [
    {
      text: 'พระราชบัญญัติ',
      icon: 'fa fa-folder',
      tags: ['3'],
      nodes: [
        {
          text: 'พระราชกำหนด',
          icon: 'fa fa-folder',
          tags: ['3'],
          nodes: [
            {
              text: 'ระเบียบ',
              icon: 'fa fa-folder',
              tags: ['3']
            }
          ]
        },
        {
          text: 'พระราชกฤษฎีกา',
          icon: 'fa fa-folder',
          tags: ['3'],
          nodes: [
            {
              text: 'ประกาศ',
              icon: 'fa fa-folder',
              tags: ['3']
            }
          ]
        }
      ]
    }
  ]

  var tree2 = [
    {
      text: 'พระราชบัญญัติ',
      icon: 'fa fa-folder',
      tags: ['3'],
      nodes: [
        {
          text: 'พระราชกำหนด',
          icon: 'fa fa-folder',
          tags: ['0'],
          nodes: [
            {
              text: 'ระเบียบ',
              icon: 'fa fa-folder',
              tags: ['0']
            }
          ]
        },
        {
          text: 'พระราชกฤษฎีกา',
          icon: 'fa fa-folder',
          tags: ['2'],
          nodes: [
            {
              text: 'ประกาศ',
              icon: 'fa fa-folder',
              tags: ['1'],
              nodes: [
                {
                  text: 'ข้อกฎหมาย 3',
                  icon: 'fa fa-file-text-o'
                }
              ]
            },
            {
              text: 'ข้อกฎหมาย 2',
              icon: 'fa fa-file-text-o'
            }
          ]
        }
      ]
    },
    {
      text: 'ข้อกฎหมาย 1',
      icon: 'fa fa-file-text-o'
    }
  ]

  var tree3 = [
    {
      text: 'กลุ่มที่ 1',
      icon: 'fa fa-folder',
      tags: ['3'],
      nodes: [
        {
          text: 'พระราชบัญญัติ',
          icon: 'fa fa-folder',
          tags: ['3'],
          nodes: [
            {
              text: 'พระราชกำหนด',
              icon: 'fa fa-folder',
              tags: ['0'],
              nodes: [
                {
                  text: 'ระเบียบ',
                  icon: 'fa fa-folder',
                  tags: ['0']
                }
              ]
            },
            {
              text: 'พระราชกฤษฎีกา',
              icon: 'fa fa-folder',
              tags: ['2'],
              nodes: [
                {
                  text: 'ประกาศ',
                  icon: 'fa fa-folder',
                  tags: ['1'],
                  nodes: [
                    {
                      text: 'ข้อกฎหมาย 3',
                      icon: 'fa fa-file-text-o'
                    }
                  ]
                },
                {
                  text: 'ข้อกฎหมาย 2',
                  icon: 'fa fa-file-text-o'
                }
              ]
            }
          ]
        },
        {
          text: 'ข้อกฎหมาย 1',
          icon: 'fa fa-file-text-o'
        }
      ]
    }
  ]
  $('#treeview').treeview({ data: tree, showTags: true })
  $('#treeview1').treeview({ data: tree2, showTags: true })
  $('#treeview2').treeview({ data: tree2, showTags: true })
  $('#treeview3').treeview({ data: tree3, showTags: true })
  $('#treeview4').treeview({ data: tree3, showTags: true })
}
