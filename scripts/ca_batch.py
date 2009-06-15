#! /usr/bin/env python

import sys,os,getopt,shutil
import ca_project

def process(cfgFile):

     # check for cfgFile
     if not os.path.exists(cfgFile):
          print cfgFile + ' does not exist'
          sys.exit(1)

     f = open(cfgFile,'r')
     for s in f:
          s = s.strip('\n')
          l = s.split('\t')

          ca_project.main(['--prep_arg','--url ' + l[0] + ' --redump',\
                           '--analyze_arg','--mode video',l[1]])

          shutil.rmtree(os.path.join(l[1],'images'))
     f.close()


def usage():
     print 'test'


def main():

     try:
          opts,args = getopt.getopt(sys.argv[1:], 'h',
                                    ['help'])
     except getopt.GetoptError:
          print 'Error: Illegal arguments'
          usage()
          sys.exit(2)
          
     for o, a in opts:
          if o in ('-h','--help'):
               usage()
               sys.exit()
          else:
               assert False, "unhandled option"
     
     if len(args) < 1:
          print 'need cfg file'
          sys.exit(1);
     
     process(args[0])


if __name__ == "__main__":
    main()
